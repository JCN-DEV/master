'use strict';

angular.module('stepApp')
    .factory('SisStudentInfoSubjSearch', function ($resource) {
        return $resource('api/_search/sisStudentInfoSubjs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
