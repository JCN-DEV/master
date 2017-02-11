'use strict';

angular.module('stepApp')
    .factory('PayScaleSearch', function ($resource) {
        return $resource('api/_search/payScales/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('PayScalesOfActiveGazette', function ($resource) {
        return $resource('api/payScales/activeGazette', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
