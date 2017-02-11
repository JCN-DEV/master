'use strict';

angular.module('stepApp')
    .factory('HrEmpOtherServiceInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpOtherServiceInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
