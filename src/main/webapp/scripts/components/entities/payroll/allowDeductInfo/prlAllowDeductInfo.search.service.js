'use strict';

angular.module('stepApp')
    .factory('PrlAllowDeductInfoSearch', function ($resource) {
        return $resource('api/_search/prlAllowDeductInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
