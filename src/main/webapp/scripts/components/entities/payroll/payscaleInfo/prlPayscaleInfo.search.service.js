'use strict';

angular.module('stepApp')
    .factory('PrlPayscaleInfoSearch', function ($resource) {
        return $resource('api/_search/prlPayscaleInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
