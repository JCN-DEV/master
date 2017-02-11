'use strict';

angular.module('stepApp')
    .factory('InstBuildingSearch', function ($resource) {
        return $resource('api/_search/instBuildings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
