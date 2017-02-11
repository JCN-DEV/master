'use strict';

angular.module('stepApp')
    .factory('InstComiteFormationSearch', function ($resource) {
        return $resource('api/_search/instComiteFormations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstComiteFormationByCurrentInst', function ($resource) {
        return $resource('api/instComiteFormations/institute/current', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
