'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveBalanceSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveBalances/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
