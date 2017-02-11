'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanMonthlyDeductionSearch', function ($resource) {
        return $resource('api/_search/employeeLoanMonthlyDeductions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
