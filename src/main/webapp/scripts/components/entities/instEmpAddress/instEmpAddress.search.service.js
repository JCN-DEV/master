'use strict';

angular.module('stepApp')
    .factory('InstEmpAddressSearch', function ($resource) {
        return $resource('api/_search/instEmpAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
  .factory('InstEmployeeAddressInstitute', function ($resource) {
                return $resource('api/instEmpAddresss/instEmployee/:id', {}, {
        'get': { method: 'GET', isArray: false}
        });
    });
