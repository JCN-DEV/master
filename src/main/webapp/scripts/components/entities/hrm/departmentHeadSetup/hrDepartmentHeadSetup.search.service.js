'use strict';

angular.module('stepApp')
    .factory('HrDepartmentHeadSetupSearch', function ($resource) {
        return $resource('api/_search/hrDepartmentHeadSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
