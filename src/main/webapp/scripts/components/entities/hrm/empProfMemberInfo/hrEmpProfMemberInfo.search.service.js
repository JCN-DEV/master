'use strict';

angular.module('stepApp')
    .factory('HrEmpProfMemberInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpProfMemberInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
