'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanRequisitionFormSearch', function ($resource) {
        return $resource('api/_search/employeeLoanRequisitionForms/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
