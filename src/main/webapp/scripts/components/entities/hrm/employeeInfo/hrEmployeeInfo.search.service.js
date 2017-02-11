'use strict';

angular.module('stepApp')
    .factory('HrEmployeeInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmployeeInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
