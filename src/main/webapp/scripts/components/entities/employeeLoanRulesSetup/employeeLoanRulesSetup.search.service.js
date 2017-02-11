'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanRulesSetupSearch', function ($resource) {
        return $resource('api/_search/employeeLoanRulesSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
