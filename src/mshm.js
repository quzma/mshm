(function (module) {
  let Rx = require('rxjs')
  let mshm = {};


  const SPECIES = {
    'boletus-titanus': {
      binominal: 'Boletus Titanus',
      traits: {
        fecundity: 0.80,
        resilience: 0.65
      },
      env: {
        temperature: [10, 14, 19],
        humidity: [70, 77, 95]
      }
    },
    'morchella-magnata': {
      binominal: 'Morchella Magnata',
      traits: {
        fecundity: 0.10,
        resilience: 0.90
      },
      env: {
        temperature: [5, 10, 19],
        humidity: [60, 70, 95]
      }
    }
  }  

  let specimen = {
    species: 'boletus-titanus',
    mycelia: [[44.9, 13.8, 0.87], [44.9, 13.9, 0.67]]
  };

mshm.mycelia$ = Rx.Observable.merge(
  Rx.Observable.from([specimen.mycelia])
);

mshm.newMycelium = (coords) => {
  specimen.mycelia.push([coords[0], coords[1], 0.5]);
}

 module.exports = mshm;

})(module);