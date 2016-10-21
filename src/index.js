
const CREDENTIALS = require('json!./creds.json');

let Rx = require('rxjs');
let mshm = require('./mshm.js');

let platform = new H.service.Platform({
    app_id: CREDENTIALS['app-id-here'],
    app_code: CREDENTIALS['app-code-here'],
    useHTTPS: true
});

let pixelRatio = devicePixelRatio > 1 ? 2 : 1;
let baseLayer = platform.getMapTileService({type: 'base'}).createTileLayer(
    'maptile',
    'reduced.day',
    256 * pixelRatio, //take bigger tiles for retina
    'png'
);

let mapHolder = document.getElementById('map');
let map = new H.Map(
    mapHolder,
    baseLayer,
    {
        pixelRatio: pixelRatio,
        center:  new H.geo.Point(40.769832, -73.974726),
        zoom: 13
    }
);
new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

let myceliaProvider = new H.datalens.Provider();

// cc = cell center
let cell = (cc) => {
  return new H.map.Rect(new H.geo.Rect(
    cc[0] + 0.05, cc[1] - 0.05, cc[0] - 0.05, cc[1] + 0.05
  )).setData({cc:cc});
};

let myceliaLayer = new H.datalens.ObjectLayer(
  myceliaProvider,
  {
    dataToRows: (data) => { return data; },
    rowToMapObject: (myc) => { return cell(myc).setStyle({
        fillColor: 'rgba(75, 0, 130, ' + myc[2] + ')',
        strokeColor: 'black', lineWidth: 1
    });}
  }
);

map.addLayer(myceliaLayer);

let updateMycelia = (mycelia) => {

  let c = mycelia.reduce((m, o) => {
    return [m[0]+o[0], m[1]+o[1]];
  }, [0,0]);
  map.setCenter(new H.geo.Point(
    c[0]/mycelia.length,
    c[1]/mycelia.length
    ));
  myceliaProvider.setData(mycelia);
};

updateMycelia([[44.9, 13.8, 0.87], [44.9, 13.9, 0.67]]);

mshm.mycelia$.subscribe(updateMycelia);

let newCellSelected = (e) => {
  console.log(e.target.getData());
  mshm.newMycelium(e.target.getData().cc);
  mshm.mycelia$.subscribe(updateMycelia);
};

let hoveredCell;

Rx.Observable.fromEvent(map, 'pointermove').subscribe((e) => {
  let coords = map.screenToGeo(
    e.currentPointer.viewportX,
    e.currentPointer.viewportY
  );
  let cc = {
    lat: parseFloat((Math.round(coords.lat * 10)/10).toFixed(1)),
    lng: parseFloat((Math.round(coords.lng * 10)/10).toFixed(1))
  };

  if (hoveredCell) {
    let hcc = hoveredCell.getData().cc;
    if (hcc.lat === cc.lat && hcc.lng === cc.lng) {
      return;
    } else {
        hoveredCell.dispose();
    }
  };

  hoveredCell = cell([cc.lat, cc.lng])
    .setStyle({
        fillColor: 'rgba(215, 177, 44, 0.4)',
        strokeColor: 'yellow',
        lineWidth: 5
    });
  Rx.Observable.fromEvent(hoveredCell, 'tap').subscribe(newCellSelected);
  map.addObject(hoveredCell);

  document.getElementById("output").innerHTML = JSON.stringify(cc)
    + '<br/>' + JSON.stringify(coords)
  ;
})