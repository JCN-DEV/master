'use strict';

angular.module('stepApp')
    .factory('HrEmpForeignTourInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpForeignTourInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
