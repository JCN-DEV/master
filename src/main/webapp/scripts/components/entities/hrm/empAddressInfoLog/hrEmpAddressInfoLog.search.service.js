'use strict';

angular.module('stepApp')
    .factory('HrEmpAddressInfoLogSearch', function ($resource) {
        return $resource('api/_search/hrEmpAddressInfoLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
