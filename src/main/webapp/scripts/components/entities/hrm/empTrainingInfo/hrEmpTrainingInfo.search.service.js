'use strict';

angular.module('stepApp')
    .factory('HrEmpTrainingInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpTrainingInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
