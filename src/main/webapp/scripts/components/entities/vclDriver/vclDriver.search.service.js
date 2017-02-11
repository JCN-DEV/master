'use strict';

angular.module('stepApp')
    .factory('VclDriverSearch', function ($resource) {
        return $resource('api/_search/vclDrivers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
