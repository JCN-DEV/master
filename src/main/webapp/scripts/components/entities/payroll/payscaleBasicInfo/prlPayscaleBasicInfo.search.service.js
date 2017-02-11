'use strict';

angular.module('stepApp')
    .factory('PrlPayscaleBasicInfoSearch', function ($resource) {
        return $resource('api/_search/prlPayscaleBasicInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
