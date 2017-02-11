'use strict';

angular.module('stepApp')
    .factory('ProfessorApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/professorApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
