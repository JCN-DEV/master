'use strict';

angular.module('stepApp')
    .factory('HrDepartmentSetupSearch', function ($resource) {
        return $resource('api/_search/hrDepartmentSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
