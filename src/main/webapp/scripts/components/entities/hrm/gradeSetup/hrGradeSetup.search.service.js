'use strict';

angular.module('stepApp')
    .factory('HrGradeSetupSearch', function ($resource) {
        return $resource('api/_search/hrGradeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
