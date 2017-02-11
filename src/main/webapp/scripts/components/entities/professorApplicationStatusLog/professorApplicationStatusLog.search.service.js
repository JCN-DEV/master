'use strict';

angular.module('stepApp')
    .factory('ProfessorApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/professorApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ProfessorApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/professorApplicationByEmployee/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
