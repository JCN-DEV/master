'use strict';

angular.module('stepApp')
    .factory('HrEmpTransferApplInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpTransferApplInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
