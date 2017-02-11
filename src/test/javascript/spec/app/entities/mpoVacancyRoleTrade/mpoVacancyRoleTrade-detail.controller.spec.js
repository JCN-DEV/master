'use strict';

describe('MpoVacancyRoleTrade Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoVacancyRoleTrade, MockMpoVacancyRole, MockUser, MockCmsTrade;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoVacancyRoleTrade = jasmine.createSpy('MockMpoVacancyRoleTrade');
        MockMpoVacancyRole = jasmine.createSpy('MockMpoVacancyRole');
        MockUser = jasmine.createSpy('MockUser');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoVacancyRoleTrade': MockMpoVacancyRoleTrade,
            'MpoVacancyRole': MockMpoVacancyRole,
            'User': MockUser,
            'CmsTrade': MockCmsTrade
        };
        createController = function() {
            $injector.get('$controller')("MpoVacancyRoleTradeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoVacancyRoleTradeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
