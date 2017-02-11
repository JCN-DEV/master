'use strict';

angular.module('stepApp')
    .factory('PgmsPenGrSetupSearch', function ($resource) {
        return $resource('api/_search/pgmsPenGrSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
