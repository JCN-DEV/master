'use strict';

angular.module('stepApp')
    .factory('InstituteBuildingSearch', function ($resource) {
        return $resource('api/_search/instituteBuildings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
