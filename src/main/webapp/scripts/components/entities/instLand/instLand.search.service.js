'use strict';

angular.module('stepApp')
    .factory('InstLandSearch', function ($resource) {
        return $resource('api/_search/instLands/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
