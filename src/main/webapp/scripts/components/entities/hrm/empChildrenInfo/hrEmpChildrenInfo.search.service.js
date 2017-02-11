'use strict';

angular.module('stepApp')
    .factory('HrEmpChildrenInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpChildrenInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
