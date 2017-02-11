'use strict';

angular.module('stepApp')
    .factory('MpoVacancyRoleDesgnationsSearch', function ($resource) {
        return $resource('api/_search/mpoVacancyRoleDesgnationss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoVacancyRoleDesgnationsByRole', function ($resource) {
        return $resource('api/mpoVacancyRoleDesgnationss/mpoVacancyRoles/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
