'use strict';

angular.module('stepApp')
    .factory('HrDepartmentHeadInfoSearch', function ($resource) {
        return $resource('api/_search/hrDepartmentHeadInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
