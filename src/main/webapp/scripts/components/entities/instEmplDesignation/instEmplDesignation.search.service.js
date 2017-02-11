'use strict';

angular.module('stepApp')
    .factory('InstEmplDesignationSearch', function ($resource) {
        return $resource('api/_search/instEmplDesignations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }) .factory('ActiveInstEmplDesignation', function ($resource) {
        return $resource('api/allInstEmplDesignationsList/status/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveInstEmplDesignationByType', function ($resource) {
        return $resource('api/instEmplDesignations/designationType/:type', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveInstEmplDesignationForTeacherByDesignation', function ($resource) {
        return $resource('api/instEmplDesignations/teacher/byInstLevelAndType/designation/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
