'use strict';

angular.module('stepApp')
    .factory('PrlPayscaleAllowanceInfoSearch', function ($resource) {
        return $resource('api/_search/prlPayscaleAllowanceInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
