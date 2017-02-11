'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanApproveAndForwardSearch', function ($resource) {
        return $resource('api/_search/employeeLoanApproveAndForwards/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
