'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanTypeSetupSearch', function ($resource) {
        return $resource('api/_search/employeeLoanTypeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
