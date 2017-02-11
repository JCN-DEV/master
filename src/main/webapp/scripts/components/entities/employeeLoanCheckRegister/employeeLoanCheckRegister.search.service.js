'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanCheckRegisterSearch', function ($resource) {
        return $resource('api/_search/employeeLoanCheckRegisters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
