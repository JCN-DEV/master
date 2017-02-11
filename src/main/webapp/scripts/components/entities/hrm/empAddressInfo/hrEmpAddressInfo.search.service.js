'use strict';

angular.module('stepApp')
    .factory('HrEmpAddressInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpAddressInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
