'use strict';

angular.module('stepApp')
    .factory('PgmsGrObtainSpecEmpSearch', function ($resource) {
        return $resource('api/_search/pgmsGrObtainSpecEmps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
