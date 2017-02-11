'use strict';

describe('InstituteGovernBody Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteGovernBody, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteGovernBody = jasmine.createSpy('MockInstituteGovernBody');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteGovernBody': MockInstituteGovernBody,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstituteGovernBodyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteGovernBodyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
