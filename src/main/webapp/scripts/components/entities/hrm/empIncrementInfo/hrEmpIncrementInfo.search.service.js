'use strict';

angular.module('stepApp')
    .factory('HrEmpIncrementInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpIncrementInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
