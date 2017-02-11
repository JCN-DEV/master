'use strict';

angular.module('stepApp')
    .factory('MpoSalaryFlowSearch', function ($resource) {
        return $resource('api/_search/mpoSalaryFlows/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
