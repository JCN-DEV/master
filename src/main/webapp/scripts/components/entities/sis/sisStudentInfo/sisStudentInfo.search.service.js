'use strict';

angular.module('stepApp')
    .factory('SisStudentInfoSearch', function ($resource) {
        return $resource('api/_search/sisStudentInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
