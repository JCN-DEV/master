'use strict';

angular.module('stepApp')
    .factory('DistrictSearch', function ($resource) {
        return $resource('api/_search/districts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('DistrictsByName', function ($resource) {
        return $resource('api/districts/byName', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('DistrictsByDivision', function ($resource) {
        return $resource('api/districts/division/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
