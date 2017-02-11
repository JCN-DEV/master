'use strict';

angular.module('stepApp')
    .factory('HrEmpAdvIncrementInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpAdvIncrementInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
