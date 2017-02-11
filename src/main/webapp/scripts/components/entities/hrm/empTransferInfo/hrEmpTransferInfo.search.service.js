'use strict';

angular.module('stepApp')
    .factory('HrEmpTransferInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpTransferInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
