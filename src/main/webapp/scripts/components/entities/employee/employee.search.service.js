'use strict';

angular.module('stepApp')
    .factory('EmployeeSearch', function ($resource) {
        return $resource('api/_search/employees/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('EmployeeInstitute', function ($resource) {
        return $resource('api/employees/institute/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
