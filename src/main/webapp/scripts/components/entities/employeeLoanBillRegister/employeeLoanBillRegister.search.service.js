'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanBillRegisterSearch', function ($resource) {
        return $resource('api/_search/employeeLoanBillRegisters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
