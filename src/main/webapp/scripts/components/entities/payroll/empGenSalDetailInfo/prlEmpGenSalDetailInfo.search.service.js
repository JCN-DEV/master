'use strict';

angular.module('stepApp')
    .factory('PrlEmpGenSalDetailInfoSearch', function ($resource) {
        return $resource('api/_search/prlEmpGenSalDetailInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
