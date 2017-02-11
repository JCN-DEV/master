'use strict';

angular.module('stepApp')
    .factory('CmsSemesterSearch', function ($resource) {
        return $resource('api/_search/cmsSemesters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsSemesterByCodeAndName', function ($resource) {
        return $resource('api/cmsSemesters/getCmsSemesterByCodeAndName/:code/:name', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });


