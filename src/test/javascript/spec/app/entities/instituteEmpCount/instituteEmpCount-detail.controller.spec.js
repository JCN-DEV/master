'use strict';

describe('InstituteEmpCount Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteEmpCount, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteEmpCount = jasmine.createSpy('MockInstituteEmpCount');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteEmpCount': MockInstituteEmpCount,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstituteEmpCountDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteEmpCountUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
