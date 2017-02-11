'use strict';

angular.module('stepApp')
    .factory('MpoVacancyRoleTradeSearch', function ($resource) {
        return $resource('api/_search/mpoVacancyRoleTrades/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoVacancyRoleTradeByVacancyRole', function ($resource) {
        return $resource('api/mpoVacancyRoleTrades/mpoVacancyRole/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
