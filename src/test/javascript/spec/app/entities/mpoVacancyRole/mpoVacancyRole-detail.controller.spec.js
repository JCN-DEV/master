'use strict';

describe('MpoVacancyRole Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoVacancyRole, MockCmsCurriculum, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoVacancyRole = jasmine.createSpy('MockMpoVacancyRole');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoVacancyRole': MockMpoVacancyRole,
            'CmsCurriculum': MockCmsCurriculum,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("MpoVacancyRoleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoVacancyRoleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
