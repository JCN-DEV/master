'use strict';

angular.module('stepApp')
    .factory('GradeSetupSearch', function ($resource) {
        return $resource('api/_search/gradeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
